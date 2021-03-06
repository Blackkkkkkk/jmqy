package com.dhxx.web.utils;

/**
 * Created by Administrator on 2017/11/15.
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by zhangwc.
 * 全局类型转换器
 * Date:2016-12-12.
 */
public class CustomBindInitializer implements WebBindingInitializer {
    private String format = "yyyy-MM-dd";

    private boolean autoGrowNestedPaths = true;

    private boolean directFieldAccess = false;

    private MessageCodesResolver messageCodesResolver;

    private BindingErrorProcessor bindingErrorProcessor;

    private Validator validator;

    private ConversionService conversionService;

    private PropertyEditorRegistrar[] propertyEditorRegistrars;

    public void setAutoGrowNestedPaths(boolean autoGrowNestedPaths) {
        this.autoGrowNestedPaths = autoGrowNestedPaths;
    }

    public boolean isAutoGrowNestedPaths() {
        return this.autoGrowNestedPaths;
    }

    public final void setDirectFieldAccess(boolean directFieldAccess) {
        this.directFieldAccess = directFieldAccess;
    }

    public boolean isDirectFieldAccess() {
        return directFieldAccess;
    }

    public final void setMessageCodesResolver(MessageCodesResolver messageCodesResolver) {
        this.messageCodesResolver = messageCodesResolver;
    }

    public final MessageCodesResolver getMessageCodesResolver() {
        return this.messageCodesResolver;
    }

    public final void setBindingErrorProcessor(BindingErrorProcessor bindingErrorProcessor) {
        this.bindingErrorProcessor = bindingErrorProcessor;
    }

    public final BindingErrorProcessor getBindingErrorProcessor() {
        return this.bindingErrorProcessor;
    }

    public final void setValidator(Validator validator) {
        this.validator = validator;
    }

    public final Validator getValidator() {
        return this.validator;
    }

    public final void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public final ConversionService getConversionService() {
        return this.conversionService;
    }

    public final void setPropertyEditorRegistrar(PropertyEditorRegistrar propertyEditorRegistrar) {
        this.propertyEditorRegistrars = new PropertyEditorRegistrar[]{propertyEditorRegistrar};
    }

    public final void setPropertyEditorRegistrars(PropertyEditorRegistrar[] propertyEditorRegistrars) {
        this.propertyEditorRegistrars = propertyEditorRegistrars;
    }

    public final PropertyEditorRegistrar[] getPropertyEditorRegistrars() {
        return this.propertyEditorRegistrars;
    }


    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.setAutoGrowNestedPaths(this.autoGrowNestedPaths);
        SimpleDateFormat sf = new SimpleDateFormat(format);
        sf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sf, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
        binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        if (this.directFieldAccess) {
            binder.initDirectFieldAccess();
        }
        if (this.messageCodesResolver != null) {
            binder.setMessageCodesResolver(this.messageCodesResolver);
        }
        if (this.bindingErrorProcessor != null) {
            binder.setBindingErrorProcessor(this.bindingErrorProcessor);
        }
        if ((this.validator != null) && (binder.getTarget() != null) &&
                (this.validator.supports(binder.getTarget().getClass()))) {
            binder.setValidator(this.validator);
        }
        if (this.conversionService != null) {
            binder.setConversionService(this.conversionService);
        }
        if (this.propertyEditorRegistrars != null)
            for (PropertyEditorRegistrar propertyEditorRegistrar : this.propertyEditorRegistrars)
                propertyEditorRegistrar.registerCustomEditors(binder);
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
