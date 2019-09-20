package com.wesley.demomustache.controller;

import com.itextpdf.text.DocumentException;
import com.wesley.demomustache.service.MustacheService;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/mustache")
public class MustacheController
{

    MustacheService mustacheService;


    @Autowired
    public MustacheController(MustacheService mustacheService)
    {
        this.mustacheService = mustacheService;
    }


    @GetMapping("/todo")
    public String gerarPaginaTodo() throws IOException
    {
        return mustacheService.gerarHtmlTodo();
    }


    @GetMapping("/artigo")
    public ModelAndView exibirArtigo(Map<String, Object> model)
    {
        model.put("articles", mustacheService.gerarListaArtigoDTO(10));
        return new ModelAndView("index", model);
    }


    @GetMapping("/artigos_pdf")
    public ResponseEntity<Resource> baixarPDFArtigos() throws IOException, DocumentException
    {
        Resource resource = mustacheService.gerarPdfArtigos();
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/pdf"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"artigos.pdf\"")
            .body(resource);
    }
}
