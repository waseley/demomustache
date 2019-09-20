package com.wesley.demomustache.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.wesley.demomustache.dto.ArtigoDTO;
import com.wesley.demomustache.dto.TodoDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MustacheService
{
    public String gerarHtmlTodo() throws IOException
    {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("todo.mustache");
        TodoDTO todo = TodoDTO.builder().title("Todo 1").text("Description").build();
        StringWriter writer = new StringWriter();
        m.execute(writer, todo).flush();
        return writer.toString();
    }


    public List<ArtigoDTO> gerarListaArtigoDTO(int qtd)
    {
        return IntStream.range(0, qtd)
            .mapToObj(i -> gerarArtigo("Artigo no " + i))
            .collect(Collectors.toList());
    }


    public Resource gerarPdfArtigos() throws IOException, DocumentException
    {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("templates/layout/article.html");
        Map<String, Object> model = new HashMap<>();
        model.put("articles", gerarListaArtigoDTO(5));
        StringWriter writer = new StringWriter();
        m.execute(writer, model).flush();
        return new ByteArrayResource(gerarPDFFromHTML(writer.toString()));
    }


    private ArtigoDTO gerarArtigo(String titulo)
    {
        DataFactory data = new DataFactory();
        return ArtigoDTO.builder()
            .title(titulo)
            .publishDate(data.getBirthDate().toString())
            .author(data.getName())
            .body("Artigo generico gerado e escrito sem a menor ideia do que isso significa.")
            .build();
    }


    private byte[] gerarPDFFromHTML(String html) throws IOException, DocumentException
    {
        Document document = new Document();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, byteStream);
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
        document.close();
        return byteStream.toByteArray();
    }
}
