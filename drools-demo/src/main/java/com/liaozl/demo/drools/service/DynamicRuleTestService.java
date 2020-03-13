package com.liaozl.demo.drools.service;

import com.liaozl.demo.drools.entity.DynamicRuleDto;
import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.compiler.xml.rules.RuleHandler;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2020/3/13 12:13
 */
@Slf4j
@Service
public class DynamicRuleTestService {

    private static final AtomicInteger CHANGE_TIMES = new AtomicInteger();

    @Autowired
    private DynamicRuleService dynamicRuleService;

    @Autowired
    private KieSession kieSession;

    private String ruleStr = rule1();

    private void testDynamicRule2() {
        try {
            KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            knowledgeBuilder.add(ResourceFactory.newByteArrayResource(ruleStr.getBytes("utf-8")), ResourceType.DRL);

            InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addPackages(knowledgeBuilder.getKnowledgePackages());

            KieSession kieSession = kBase.newKieSession();

            kieSession.setGlobal("dynamicRuleService", dynamicRuleService);

            Globals globals = kieSession.getGlobals();
            log.info("GlobalKeys======" + globals.getGlobalKeys().toString());

            DynamicRuleDto dto = new DynamicRuleDto();
            dto.setName("未成年人");
            dto.setAge(15);

            DynamicRuleDto dto2 = new DynamicRuleDto();
            dto2.setName("成年人");
            dto2.setAge(30);

            kieSession.insert(dto);
            kieSession.insert(dto2);

            int successCount = kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("dynamicRule_"));

            log.info("======成功触发{}条规则=====", successCount);
            log.info("dtoCallRuleResult：{}", dto.getCallRuleResult());
            log.info("dto2CallRuleResult：{}", dto2.getCallRuleResult());

            kieSession.dispose();

        } catch (Exception e) {
            log.error("testDynamicRule2 ", e);
        }
    }

    public void testDynamicRule() {
        int i = 0;
        while (i <= 100) {
            try {
                Thread.sleep(1000);
                testDynamicRule2();
            } catch (Exception e) {
                log.error("testDynamicRule error:", e);
            }

            i++;
        }
    }

    @Scheduled(fixedRate = 5000)
    public void changeRule() {
        int changeTimes = CHANGE_TIMES.getAndIncrement();
        if (changeTimes % 2 == 0) {
            ruleStr = rule2();
        } else {
            ruleStr = rule1();
        }
    }

    private String rule1() {
        return "package conf.rules;\n" +
                "\n" +
                "import com.liaozl.demo.drools.entity.DynamicRuleDto;\n" +
                "import com.liaozl.demo.drools.service.DynamicRuleService;\n" +
                "\n" +
                "global DynamicRuleService dynamicRuleService;\n" +
                "\n" +
                "rule \"dynamicRule_age<18\"\n" +
                "    salience 1 // 设置优先级，值越大优先级越高\n" +
                "    no-loop true\n" +
                "    when\n" +
                "        dto:DynamicRuleDto(age<18)\n" +
                "    then\n" +
                "        String str = dynamicRuleService.doRule1Biz(dto);\n" +
                "        dto.setCallRuleResult(str);\n" +
                "        System.out.println(\"====[age<18]====\" + str);\n" +
                "end\n" +
                "\n" +
                "rule \"dynamicRule_age>=18\"\n" +
                "    salience 2 // 设置优先级，值越大优先级越高\n" +
                "    no-loop true\n" +
                "    when\n" +
                "        dto:DynamicRuleDto(age>=18)\n" +
                "    then\n" +
                "        String str = dynamicRuleService.doRule2Biz(dto);\n" +
                "        dto.setCallRuleResult(str);\n" +
                "        System.out.println(\"====[age>=18]====\" + str);\n" +
                "end";
    }

    private String rule2() {
        return "package conf.rules;\n" +
                "\n" +
                "import com.liaozl.demo.drools.entity.DynamicRuleDto;\n" +
                "import com.liaozl.demo.drools.service.DynamicRuleService;\n" +
                "\n" +
                "global DynamicRuleService dynamicRuleService;\n" +
                "\n" +
                "rule \"dynamicRule_age<18\"\n" +
                "    salience 1 // 设置优先级，值越大优先级越高\n" +
                "    no-loop true\n" +
                "    when\n" +
                "        dto:DynamicRuleDto(age<18)\n" +
                "    then\n" +
                "        String str = dynamicRuleService.doRule3Biz(dto);\n" +
                "        dto.setCallRuleResult(str);\n" +
                "        System.out.println(\"====[age<18]====\" + str);\n" +
                "end\n" +
                "\n" +
                "rule \"dynamicRule_age>=18\"\n" +
                "    salience 2 // 设置优先级，值越大优先级越高\n" +
                "    no-loop true\n" +
                "    when\n" +
                "        dto:DynamicRuleDto(age>=18)\n" +
                "    then\n" +
                "        String str = dynamicRuleService.doRule4Biz(dto);\n" +
                "        dto.setCallRuleResult(str);\n" +
                "        System.out.println(\"====[age>=18]====\" + str);\n" +
                "end";
    }
}
