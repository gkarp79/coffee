package com.coffee.constant;

public enum OrderStatus {
    ORDER, // 주문
    CANCEL, //주문취소
    ORDER_CHECK, // 주문확인
    MONEY_CHECK, //입금확인
    PROCESSING, // 출고준비중
    DONE, // 출고 완료

    EXCHANGE, //교환

    EXCHANGE_DONE, //교환완료

    REFUND, //환불

    REFUND_DONE //환불완료

}