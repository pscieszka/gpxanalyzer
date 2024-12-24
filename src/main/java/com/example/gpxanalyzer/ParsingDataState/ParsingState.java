package com.example.gpxanalyzer.ParsingDataState;

import org.w3c.dom.*;

public interface ParsingState<T> {
    T handle(Document data);
}
