package com.cryptorecommendation.controller.command;

public interface Command<RequestT, ResponseT> {

    ResponseT execute(RequestT request);
}
