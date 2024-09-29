package edu.school21.processor;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<meta charset=\"UTF-8\">\n");
            htmlBuilder.append("<meta name=\"referrer\" content=\"no-referrer-when-downgrade\">\n");
            htmlBuilder.append("<form action=\"").append(htmlForm.action())
                    .append("\" method=\"").append(htmlForm.method()).append("\">\n");

            for (Element enclosed : element.getEnclosedElements()) {
                HtmlInput htmlInput = enclosed.getAnnotation(HtmlInput.class);
                if (htmlInput != null) {
                    htmlBuilder.append("\t<input type=\"").append(htmlInput.type())
                            .append("\" name=\"").append(htmlInput.name())
                            .append("\" placeholder=\"").append(htmlInput.placeholder())
                            .append("\">\n");
                }
            }
            htmlBuilder.append("\t<input type=\"submit\" value=\"Send\">\n");
            htmlBuilder.append("</form>\n");

            try (Writer writer = processingEnv.getFiler().createResource(javax.tools.StandardLocation.CLASS_OUTPUT, "", htmlForm.fileName()).openWriter()) {
                writer.write(htmlBuilder.toString());
                System.out.println("HTML file generated: " + htmlForm.fileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
