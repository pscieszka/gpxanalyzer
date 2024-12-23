package com.example.gpxanalyzer.ParsingDataState;

interface ParsingState<T> {
    T handle(String data);
}
