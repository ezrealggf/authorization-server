package org.ashe.auth.domain.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 当发生错误或异常时的对象体
 */
@Slf4j
@SuppressWarnings("all")
public class ErrorBody {

    /**
     * 异常类名
     */
    private String throwable;
    /**
     * 异常抛出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "GMT+8")
    // jackson是spring-boot的web/webflux框架默认依赖的json库
    private Date throwTime;
    /**
     * 异常消息
     */
    private String message;
    /**
     * 异常堆栈
     */
    private String stackTrace;
    /**
     * 异常源数据
     */
    private Map<String, Object> metadata;

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    public Date getThrowTime() {
        return throwTime == null ? null : (Date) throwTime.clone();
    }

    public void setThrowTime(Date throwTime) {
        if (throwTime != null) {
            this.throwTime = (Date) throwTime.clone();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public ErrorBody(String throwable, Date throwTime, String message, String stackTrace, Map<String, Object> metadata) {
        this.throwable = throwable;
        if (throwTime != null) {
            this.throwTime = (Date) throwTime.clone();
        }
        this.message = message;
        this.stackTrace = stackTrace;
        this.metadata = metadata;
    }

    /**
     * 获取ErrorData通过 Throwable 默认不收集堆栈信息
     */
    public static ErrorBody build(Throwable e) {
        return build(e, false);
    }

    /**
     * 获取ErrorData通过 Throwable
     *
     * @param e                 异常
     * @param collectStackTrace 是否收集堆栈
     */
    public static ErrorBody build(Throwable e, boolean collectStackTrace) {
        return new ErrorBody(
                e.getClass().getName(), new Date(),
                e.getMessage(),
                collectStackTrace ? collectStackTrace(e) : null,
                new TreeMap<>());
    }

    /**
     * 获取ErrorData通过 Throwable
     *
     * @param e                 异常
     * @param printStackTrace   是否打印堆栈
     * @param collectStackTrace 是否收集堆栈
     */
    public static ErrorBody build(Throwable e, boolean printStackTrace, boolean collectStackTrace) {
        if (printStackTrace) {
            log.error("printStackTrace", e);
        }
        return build(e, collectStackTrace);
    }


    /**
     * 收集异常堆栈信息
     */
    private static String collectStackTrace(Throwable e) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw, true)) {
            e.printStackTrace(pw);
            return sw.toString();
        } catch (IOException ex) {
            return "collectStackTrace exception : " + ex.getMessage();
        }
    }

}