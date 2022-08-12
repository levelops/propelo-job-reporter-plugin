package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsResponse {

    @JsonProperty("records")
    private final List<Product> records;

    public ProductsResponse() {
        records = new ArrayList<>();
    }

    public ProductsResponse(List<Product> records) {
        this.records = records;
    }

    public List<Product> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductsResponse that = (ProductsResponse) o;
        return Objects.equals(records, that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(records);
    }

    /*
{
    "_metadata": {
        "page_size": 100,
        "page": 0,
        "total_count": 1
    },
    "records": [
        {
            "name": "testdash",
            "organization_id": "1",
            "id": "1",
            "jira_projects": [
                "asd"
            ],
            "git_repos": [
                "bsd"
            ],
            "created_at": 123123123123,
            "updated_at": 123123123123
        }
    ]
}
 */

}
