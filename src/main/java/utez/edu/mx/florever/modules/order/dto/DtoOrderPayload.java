package utez.edu.mx.florever.modules.order.dto;

import java.util.List;

public class DtoOrderPayload {
private String status;
private Long category;
private List<DtoFlowers> flowers;


    public DtoOrderPayload(List<DtoFlowers> flowers, Long category, String status) {
        this.flowers = flowers;
        this.category = category;
        this.status = status;
    }
    public DtoOrderPayload() {}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public List<DtoFlowers> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<DtoFlowers> flowers) {
        this.flowers = flowers;
    }
}
