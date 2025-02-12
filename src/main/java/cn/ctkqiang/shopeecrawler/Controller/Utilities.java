package cn.ctkqiang.shopeecrawler.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.ctkqiang.shopeecrawler.Constants.Names;

/**
 * 通用工具类
 * 该类提供了一系列实用工具方法，主要用于处理数据导出、格式化和时间控制等功能。
 * 包含CSV文件导出、数据格式化以及执行延时等基础功能。
 */
public class Utilities {
    private static final String APPLICATION_NAME = Names.APPLICATION_NAME;

    /**
     * 将数据导出为CSV格式文件
     * 该方法接收一个数据列表，将其转换为CSV格式并写入指定文件。
     * 导出过程包括：
     * 1. 自动生成CSV文件头（基于Map的键值）
     * 2. 逐行写入数据内容
     * 3. 自动处理特殊字符和格式化
     * 
     * @param Data     要导出的数据列表，每个Map代表一行数据，其中key为列名，value为单元格值
     * @param FilePath 输出CSV文件的完整路径
     * @throws IOException 当文件写入过程中发生错误时抛出异常
     */
    protected void ExportToCSV(List<Map<String, String>> Data, String FilePath) throws IOException {
        try (FileWriter writer = new FileWriter(FilePath)) {
            if (!Data.isEmpty()) {
                writer.write(String.join(",", Data.get(0).keySet()) + "\n");

                for (Map<String, String> row : Data) {
                    writer.write(FormatCsvRow(row) + "\n");
                }
            }
        } catch (IOException e) {
            throw new IOException(String.format("%s 写入文件时发生错误：", Utilities.APPLICATION_NAME) + e.getMessage(), e);
        }
    }

    /**
     * 格式化单行CSV数据
     * 将Map格式的数据行转换为CSV格式的字符串。
     * 处理过程包括：
     * 1. 提取Map中的所有值
     * 2. 对每个值进行CSV转义处理
     * 3. 使用逗号连接所有处理后的值
     * 
     * @param row 包含单行数据的Map对象
     * @return 格式化后的CSV行字符串，字段间用逗号分隔
     */
    protected String FormatCsvRow(Map<String, String> row) {
        return row.values().stream()
                .map(this::EscapeCsvField)
                .collect(java.util.stream.Collectors.joining(","));
    }

    /**
     * CSV字段转义处理
     * 处理CSV字段中的特殊字符，确保输出的CSV文件格式正确。
     * 主要处理：
     * 1. 空值转换为空字符串
     * 2. 双引号转义（将单个双引号替换为两个双引号）
     * 3. 包含逗号的字段添加双引号包围
     * 
     * @param field 需要处理的字段值
     * @return 经过转义处理的字段值
     */
    protected String EscapeCsvField(String field) {
        if (field == null)
            return "";
        String escaped = field.replaceAll("\"", "\"\"");
        return field.contains(",") ? "\"" + escaped + "\"" : escaped;
    }

    /**
     * 程序执行延时控制
     * 使当前线程暂停指定的时间，主要用于：
     * 1. 控制爬虫访问频率
     * 2. 防止对目标服务器造成过大压力
     * 3. 模拟人工操作间隔
     * 
     * @param seconds 需要延时的秒数
     *                注意：当线程被中断时，会重新设置中断标志
     */
    protected void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}