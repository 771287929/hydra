package com.jd.bdp.hydra.mysql.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.bdp.hydra.Annotation;
import com.jd.bdp.hydra.BinaryAnnotation;
import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.mysql.persistent.dao.AnnotationMapper;
import com.jd.bdp.hydra.mysql.persistent.dao.SpanMapper;
import com.jd.bdp.hydra.mysql.persistent.dao.TraceMapper;
import com.jd.bdp.hydra.mysql.persistent.entity.Absannotation;
import com.jd.bdp.hydra.mysql.persistent.entity.Trace;
import com.jd.bdp.hydra.store.inter.InsertService;

/**
 * User: biandi Date: 13-5-9 Time: 下午4:13
 */
public class InsertServiceImpl implements InsertService {

	private Logger logger=LoggerFactory.getLogger(InsertServiceImpl.class);
	@Override
	public void addSpan(Span span) {
		logger.debug("addSpan,span={}",span.toString());
		
		if (span.getServiceId() != null) {
			if (!Utils.isRoot(span) || Utils.isRoot(span) && Utils.isTopAnntation(span)) {
				spanMapper.addSpan(span);
			}else if(Utils.isRoot(span)&&Utils.getSsAnnotation(span.getAnnotations())!=null){
				spanMapper.addSpan(span);
			}
			
			
		}
	}

	@Override
	public void addTrace(Span span) {
		
		logger.debug("addTrace,span={}",span.toString());
		/*if (Utils.isTopAnntation(span) && Utils.isRoot(span)) {
			Annotation annotation = Utils.getCrAnnotation(span.getAnnotations());
			Annotation annotation1 = Utils.getCsAnnotation(span.getAnnotations());
			Trace t = new Trace();
			t.setTraceId(span.getTraceId());
			t.setDuration((int) (annotation.getTimestamp() - annotation1.getTimestamp()));
			t.setService(span.getServiceId());
			t.setTime(annotation1.getTimestamp());
			traceMapper.addTrace(t);
		} else if (!Utils.isRoot(span)) { // 增加这部分逻辑为了追踪dubbox
											// restful服务，因为只有这两部分, html5－－>dubbo服务client-->dubbo服务server
			Annotation annotation = Utils.getCrAnnotation(span.getAnnotations()) == null
					? Utils.getSsAnnotation(span.getAnnotations()) : Utils.getCrAnnotation(span.getAnnotations());
			Annotation annotation1 = Utils.getCsAnnotation(span.getAnnotations()) == null
					? Utils.getSrAnnotation(span.getAnnotations()) : Utils.getCrAnnotation(span.getAnnotations());
			Trace t = new Trace();
			t.setTraceId(span.getTraceId());
			if (annotation == null || annotation1 == null) {
				t.setDuration(0);
			} else {
				t.setDuration((int) (annotation.getTimestamp() - annotation1.getTimestamp()));
			}
			t.setService(span.getServiceId());
			t.setTime(annotation1.getTimestamp());
			traceMapper.addTrace(t);
		}*/
		
		if(Utils.isRoot(span)){
			Annotation annotation = Utils.getCrAnnotation(span.getAnnotations()) == null
					? Utils.getSsAnnotation(span.getAnnotations()) : Utils.getCrAnnotation(span.getAnnotations());
			Annotation annotation1 = Utils.getCsAnnotation(span.getAnnotations()) == null
					? Utils.getSrAnnotation(span.getAnnotations()) : Utils.getCrAnnotation(span.getAnnotations());
			Trace t = new Trace();
			t.setTraceId(span.getTraceId());
			if (annotation == null || annotation1 == null) {
				t.setDuration(0);
			} else {
				t.setDuration((int) (annotation.getTimestamp() - annotation1.getTimestamp()));
			}
			t.setService(span.getServiceId());
			t.setTime(annotation1.getTimestamp());
			traceMapper.addTrace(t);
		}
	}

	@Override
	public void addAnnotation(Span span) {
		
		logger.debug("addAnnotation,span={}",span.toString());
		for (Annotation a : span.getAnnotations()) {
			Absannotation aa = new Absannotation(a, span);
			annotationMapper.addAnnotation(aa);
		}

		for (BinaryAnnotation b : span.getBinaryAnnotations()) {
			Absannotation bb = new Absannotation(b, span);
			annotationMapper.addAnnotation(bb);
		}
	}

	private AnnotationMapper annotationMapper;
	private SpanMapper spanMapper;
	private TraceMapper traceMapper;

	public void setAnnotationMapper(AnnotationMapper annotationMapper) {
		this.annotationMapper = annotationMapper;
	}

	public void setSpanMapper(SpanMapper spanMapper) {
		this.spanMapper = spanMapper;
	}

	public void setTraceMapper(TraceMapper traceMapper) {
		this.traceMapper = traceMapper;
	}
}
