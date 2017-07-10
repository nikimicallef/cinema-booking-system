package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.TheatresEntity.Theatre;

/**
 * Gives the {@link Theatre#rows}, {@link Theatre#columns} and {@link Theatre#id} for a given {@link Theatre}
 * @return
 */
@JsonPropertyOrder({ "theatre_id", "rows", "columns" })
public class TheatreInformationResponse {
    @JsonProperty("theatre_id")
    private Long theatreId;
    private Integer rows;
    private Integer columns;

    public TheatreInformationResponse(final Long theatreId, final Integer rows, final Integer columns) {
        this.theatreId = theatreId;
        this.rows = rows;
        this.columns = columns;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getColumns() {
        return columns;
    }
}
