package whiteman.mc.status.dto;

import lombok.Data;

@Data
public class McServerStatusDto {
	private McVersionDto version;
	private McPlayersDto players;
	private McDescriptionDto description;
	private String favicon;
	private boolean enforcesSecureChat;
	private boolean previewsChat;
}
