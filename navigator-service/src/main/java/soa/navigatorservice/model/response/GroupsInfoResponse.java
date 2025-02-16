package soa.navigatorservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.navigatorservice.model.dto.GroupInfoDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupsInfoResponse {
    private List<GroupInfoDto> groups;
}
