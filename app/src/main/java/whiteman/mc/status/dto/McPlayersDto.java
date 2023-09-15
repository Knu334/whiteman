package whiteman.mc.status.dto;

import java.util.List;

import lombok.Data;

@Data
public class McPlayersDto {
	private int max;
	private int online;
	private List<McSampleDto> sample;
}
