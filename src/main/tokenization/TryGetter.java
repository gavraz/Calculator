package main.tokenization;

import java.io.BufferedInputStream;
import java.io.IOException;

interface TryGetter {
    Token tryGetNext(String input, int i);
}
