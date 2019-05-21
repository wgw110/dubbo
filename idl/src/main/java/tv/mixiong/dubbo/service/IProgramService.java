package tv.mixiong.dubbo.service;

import tv.mixixiong.entity.ProgramFormBean;

import java.util.Map;

public interface IProgramService {
    String sayHello(String input);
    void saveProgram(ProgramFormBean program);
    void updateProgram(Map<String,Object> sets, Map<String,Object> wheres);
}
