package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.trade.Main;
import com.alipay.trade.config.Configs;
import com.alipay.trade.model.ExtendParams;
import com.alipay.trade.model.GoodsDetail;
import com.alipay.trade.model.TradeStatus;
import com.alipay.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.trade.model.result.AlipayF2FPayResult;
import com.alipay.trade.service.AlipayTradeService;
import com.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.vo.GunsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨盛
 * @date 2019/12/2 23:30
 */

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();
    }

    @Autowired
    MoocOrderTMapper moocOrderTMapper;

    @Override
    public Integer isTrueSeats(String fieldId, String seats) {
        return null;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVo saveOrderInfo(String filedId, String soldSeats, String seatsName, String userName) {
        return null;
    }

    @Override
    public GunsVo getOrderInfoByUserName(String username, Integer nowPage, Integer pageSize) {
        return null;
    }

    @Override
    public String getSoldSeatsByFileId(Integer fileId) {
        return null;
    }

    @Override
    public OrderVo isPay(Integer orderId) {
        // 查询订单状态
        MoocOrderT moocOrder = moocOrderTMapper.selectById(orderId);
        boolean tradePay = test_trade_pay(tradeService, moocOrder);
        if (tradePay == true) {
            // 支付成功，修改订单状态
            EntityWrapper<MoocOrderT> orderWrapper = new EntityWrapper<>();
            orderWrapper.eq("order_statue", 1);
            moocOrderTMapper.update(moocOrder, orderWrapper);
            OrderVo orderVo = convert2OrderVo(moocOrder);
            return orderVo;
        }
        OrderVo orderVo = convert2OrderVo(moocOrder);
        return orderVo;
    }

    @Override
    public int updateOrderStatusById() {
        return 0;
    }

    private OrderVo convert2OrderVo(MoocOrderT moocOrder) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(moocOrder, orderVo);
        return orderVo;
    }

    // 测试当面付2.0支付
    public boolean test_trade_pay(AlipayTradeService service, MoocOrderT moocOrder) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradepay" + System.currentTimeMillis()
                + moocOrder.getUuid();


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = "xxx品牌xxx门店当面付消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = moocOrder.getOrderPrice().toString();

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        //        String discountableAmount = "1.00"; //

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);

        String appAuthToken = "应用授权令牌";//根据真实值填写

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                //            .setAppAuthToken(appAuthToken)
                .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                .setTotalAmount(totalAmount).setStoreId(storeId)
                .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
                .setExtendParams(extendParams).setSellerId(sellerId)
                .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = service.tradePay(builder);
        if (TradeStatus.SUCCESS.equals(result.getTradeStatus())) {
            // 支付成功
            return true;
        }
        return false;
    }
}
