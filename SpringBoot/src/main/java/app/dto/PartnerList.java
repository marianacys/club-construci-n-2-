package app.dto;

import java.util.ArrayList;
import java.util.List;

public class PartnerList {
    private List<PartnerDto> partners;

    public PartnerList() {
        this.partners = new ArrayList<>();
    }

    // Agregar un socio
    public void addPartner(PartnerDto partner) {
        this.partners.add(partner);
    }

    // Eliminar un socio
    public void removePartner(PartnerDto partner) {
        this.partners.remove(partner);
    }

    // Buscar socio por ID
    public PartnerDto findPartnerById(int id) {
        for (PartnerDto partner : partners) {
            if (partner.getId() == id) {
                return partner;
            }
        }
        return null;
    }

    // Obtener todos los socios VIP
    public List<PartnerDto> getVIPPartners() {
        List<PartnerDto> vipPartners = new ArrayList<>();
        for (PartnerDto partner : partners) {
            if ("VIP".equals(partner.getType())) {
                vipPartners.add(partner);
            }
        }
        return vipPartners;
    }

    // Contar el número de socios VIP
    public long countVIPPartners() {
        return partners.stream()
                .filter(partner -> "VIP".equals(partner.getType()))
                .count();
    }

    // Obtener todos los socios
    public List<PartnerDto> getAllPartners() {
        return new ArrayList<>(this.partners);
    }
    
    
}
