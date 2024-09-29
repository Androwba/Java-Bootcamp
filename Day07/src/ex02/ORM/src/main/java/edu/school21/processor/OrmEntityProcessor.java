package edu.school21.processor;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("edu.school21.annotations.OrmEntity")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class OrmEntityProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(OrmEntity.class);
        for (Element e : elements) {
            try {
                createTable(e);
            } catch (IOException ioException) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ioException.toString());
            }
        }
        return true;
    }

    private void createTable(Element element) throws IOException {
        OrmEntity ormEntity = element.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS " + tableName + ";\n");
        sql.append("CREATE TABLE " + tableName + " (");
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getAnnotation(OrmColumnId.class) != null) {
                sql.append("id BIGINT AUTO_INCREMENT PRIMARY KEY, ");
            } else if (enclosedElement.getAnnotation(OrmColumn.class) != null) {
                OrmColumn ormColumn = enclosedElement.getAnnotation(OrmColumn.class);
                sql.append(ormColumn.name()).append(" ").append(mapJavaTypeToSqlType(enclosedElement.asType().toString(), ormColumn.length())).append(", ");
            }
        }
        sql.setLength(sql.length() - 2);
        sql.append(");");
        writeSqlToFile(sql.toString());
    }

    private String mapJavaTypeToSqlType(String type, int length) {
        switch (type) {
            case "java.lang.String":
                return "VARCHAR(" + length + ")";
            case "java.lang.Integer":
                return "INT";
            case "java.lang.Double":
                return "DOUBLE";
            case "java.lang.Boolean":
                return "BOOLEAN";
            case "java.lang.Long":
                return "BIGINT";
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    private void writeSqlToFile(String sql) throws IOException {
        File fileObj = new File("./target/classes/schema.sql");
        boolean fileExists = fileObj.exists();
        BufferedWriter writer;
        if (!fileExists) {
            writer = new BufferedWriter(new FileWriter(fileObj));
        } else {
            writer = new BufferedWriter(new FileWriter(fileObj, true));
        }
        try (writer) {
            writer.write(sql);
            writer.newLine();
        }
    }
}
