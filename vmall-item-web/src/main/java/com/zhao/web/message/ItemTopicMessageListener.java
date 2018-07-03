package com.zhao.web.message;

import com.zhao.pojo.Item;
import com.zhao.pojo.ItemDesc;
import com.zhao.service.ItemDescService;
import com.zhao.service.ItemService;
import com.zhao.web.pojo.ItemExt;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 13:59
 * @描述
 */
public class ItemTopicMessageListener implements MessageListener{
    @Autowired
    private ItemDescService descService;
    @Autowired
    private ItemService itemService;
    @Value("${htmlPath}")
    private String htmlPath;
    @Autowired
    private FreeMarkerConfigurer configurer;

    @Override
    public void onMessage(Message message) {
        try {
            //商品id
            Long itemId = Long.parseLong(((TextMessage)message).getText());
            Thread.sleep(1000);
            //查询数据库
            Item item = itemService.getItemById(itemId);
            ItemDesc itemDesc = descService.getById(itemId);
            //模板数据
            Map data = new HashMap<>();
            data.put("item",new ItemExt(item));
            data.put("itemDesc",itemDesc);
            Configuration configuration = configurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //输出到磁盘html文件中
            FileWriter writer = new FileWriter(htmlPath+itemId+".html");
            template.process(data,writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}