package com.example.springsecurity.lowleveldesignpattern.creational.factory;

public class DocumentFactory {
    public static void main(String[] args) {
        DocumentCreationFactory pdf = new PdfDocumentFactory();
        DocumentCreationFactory word = new WordDocumentFactory();
        DocumentCreationFactory spreadSheet = new SpreadSheetDocumentFactory();
        pdf.openDoc();
        word.openDoc();
        spreadSheet.openDoc();
    }
}
interface Document{
    void open();
    void close();
}
class PdfDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening PDF");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF");
    }
}
class WordDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening Word");
    }

    @Override
    public void close() {
        System.out.println("Closing Word");
    }
}
class SpreadSheetDocument implements Document{
    @Override
    public void open() {
        System.out.println("SpreadSheet PDF");
    }

    @Override
    public void close() {
        System.out.println("SpreadSheet PDF");
    }
}

abstract class DocumentCreationFactory{
    private Document document;
    public abstract Document createDocument();
    private Document getDocument() {
        if (document == null) {
            document = createDocument();
        }
        return document;
    }
    public void openDoc(){
        Document document = getDocument();
        document.open();
    }
    public void closeDoc(){
        Document document = getDocument();
        document.close();
    }
}

class PdfDocumentFactory extends DocumentCreationFactory{
    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}
class WordDocumentFactory extends DocumentCreationFactory{
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

class SpreadSheetDocumentFactory extends DocumentCreationFactory{
    @Override
    public Document createDocument() {
        return new SpreadSheetDocument();
    }
}