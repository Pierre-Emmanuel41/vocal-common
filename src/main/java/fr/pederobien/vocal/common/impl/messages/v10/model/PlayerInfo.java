package fr.pederobien.vocal.common.impl.messages.v10.model;

public class PlayerInfo {
	private String name;
	private boolean isMute, isDeafen, isMuteByMainPlayer;

	/**
	 * Creates a player description containing the name, the mute status and the deafen status.
	 * 
	 * @param name               The playerName.
	 * @param isMute             The player mute status.
	 * @param isDeafen           The player deafen status.
	 * @param isMuteByMainPlayer True if the player associated to the given name is mute by the client main player.
	 */
	public PlayerInfo(String name, boolean isMute, boolean isDeafen, boolean isMuteByMainPlayer) {
		this.name = name;
		this.isMute = isMute;
		this.isDeafen = isDeafen;
		this.isMuteByMainPlayer = isMuteByMainPlayer;
	}

	/**
	 * @return The player name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The player mute status.
	 */
	public boolean isMute() {
		return isMute;
	}

	/**
	 * @return The player deafen status.
	 */
	public boolean isDeafen() {
		return isDeafen;
	}

	/**
	 * @return True if the player associated to this description is mute by the client main player, false otherwise.
	 */
	public boolean isMuteByMainPlayer() {
		return isMuteByMainPlayer;
	}
}
