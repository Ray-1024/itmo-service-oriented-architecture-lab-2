package soa.collectionservice.collectionservice.model.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.collectionservice.collectionservice.model.dto.GroupInfoDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupsInfoResponse {
    @XmlElement(name = "group")
    private List<GroupInfoDto> groups;
}
