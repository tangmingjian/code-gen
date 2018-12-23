package com.yiyi.tang.codegen;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author tangmingjian 2018-12-22 下午3:37
 **/
@Data
@Builder
public class TableToModel {
    @NonNull
    private String tableName;
    private String modelName;
    @NonNull
    private String idType;
}
