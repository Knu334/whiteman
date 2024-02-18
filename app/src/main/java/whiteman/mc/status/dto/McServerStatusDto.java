package whiteman.mc.status.dto;

import lombok.Data;

@Data
public class McServerStatusDto {
	private McVersionDto version;
	private String favicon;
	private String description;
	private McPlayersDto players;
}
