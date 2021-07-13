package com.example.swaggerpdf;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

class SwaggerPdfApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void generateAsciiDocs() throws Exception {
        //输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutPathSecuritySection()
                .build();
        //修改为需要转成文档的 swagger 地址
        String file = "src/docs/asciidoc/generated/all";
        Swagger2MarkupConverter.from(new URL("https://petstore.swagger.io/v2/swagger.json"))
                .withConfig(config)
                .build()
                .toFile(Paths.get(file));

        String docFilePath = "src/docs/asciidoc/generated/all.adoc";
        String newCnDoc = "src/docs/asciidoc/generated/all_zh_CN.adoc";
        File docFile = new File(docFilePath);
        File cnDoc = new File(newCnDoc);
        // 清理历史
        if(cnDoc.exists()){
            cnDoc.delete();
        }

        CharSource charSource = Files.asCharSource(docFile, Charsets.UTF_8);
        Files.asCharSink(cnDoc, Charsets.UTF_8,FileWriteMode.APPEND)
                .write("include::../config/_attributes.adoc[中文属性配置]\n");
        charSource.copyTo(Files.asCharSink(cnDoc, Charsets.UTF_8, FileWriteMode.APPEND));
        // 删除非配置项
        if(docFile.exists()){
            docFile.delete();
        }
    }
}
