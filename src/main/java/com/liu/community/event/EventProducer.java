package com.liu.community.event;

import com.alibaba.fastjson.JSONObject;
import com.liu.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: community
 * @Package: com.liu.community.event
 * @ClassName: EventProducer
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/30 20:47
 * @Version: 1.0
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireEvent(Event event) {
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
        //将Event对象转化为(串行化为)json格式的string数据，再以其topic发送出去
    }
}
