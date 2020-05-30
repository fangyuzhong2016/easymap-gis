package com.easymap.geometry.operator;

/**
 * 定义基本OperatorFactory接口的抽象类。
 * 创建几何操作实例的抽象工厂类
 */
public abstract class OperatorFactory {

    /**
     * 如果给定的运算符存在，则返回True。
     * 该类型是Operator :: Type值或用户定义的值之一。
     *
     * @param type 几何操作类型
     * @return
     */
    public abstract boolean isOperatorSupported(OperatorType type);

    /**
     * 返回给定类型的运算符。 如果不支持该运算符，则引发异常
     *
     * @param type 几何操作类型
     * @return 几何操作类型
     */
    public abstract Operator getOperator(OperatorType type);
}
