
/**
 * hydra监控，生成的traceid是独立的，无法与java web系统的traceId进行串起来，原因
 * hydra主要针对dubbo rpc 调用内容监控，不包含web入口的请求追踪。
 * 但我们一般情况都有遇到需要根据将入口的web请求与后台的rpc调用整个的调用链串起来，
 * 
 * 
 * rpc调用能过filter进行跟踪;
 * 
 */
package com.jd.bdp.hydra.dubbo;