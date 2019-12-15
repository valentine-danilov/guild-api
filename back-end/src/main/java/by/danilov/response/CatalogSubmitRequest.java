package by.danilov.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogSubmitRequest<T> {

    T content;
    private boolean deleted;
    private boolean modified;
    private boolean created;

}
