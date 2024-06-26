package com.joehxblog.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Tools {

    private static Set<Tool> getDefaultTools() {
        var ladder = new ToolType("Ladder", 199, true, false);
        var chainsaw = new ToolType("Chainsaw", 149, false, true);
        var jackhammer = new ToolType("Jackhammer", 299, false, false);

        return Set.of(
                new Tool("CHNS", chainsaw, "Stihl"),
                new Tool("LADW", ladder, "Werner"),
                new Tool("JAKD", jackhammer, "DeWalt"),
                new Tool("JAKR", jackhammer, "Ridgid")
        );
    }

    private final Map<String, Tool> tools = new HashMap<>();

    public Tools() {
        this(getDefaultTools());
    }

    public Tools(Set<Tool> tools) {
        tools.forEach(tool -> this.tools.put(tool.code(), tool));
    }

    public Tool getTool(String toolCode) {
        return this.tools.get(toolCode);
    }
}
