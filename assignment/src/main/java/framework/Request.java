package framework;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Request {
    private final URL requestURL;
    private final Session session;
}
