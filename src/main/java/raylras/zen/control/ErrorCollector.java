package raylras.zen.control;

import raylras.zen.ast.Node;

import java.util.LinkedList;

public class ErrorCollector {

    private static class Message {
        public final Node node;
        public final String message;
        public Message(Node node, String message) {
            this.node = node;
            this.message = message;
        }
    }

    private LinkedList<Message> errors;



}
