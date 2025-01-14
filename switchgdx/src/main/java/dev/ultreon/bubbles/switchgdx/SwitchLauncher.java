package dev.ultreon.bubbles.switchgdx;
import com.thelogicmaster.switchgdx.SwitchApplication;
import dev.ultreon.quantum.client.QuantumVoxel;

/** Launches the switch (SwitchGDX) application. */
public class SwitchLauncher {
	public static void main(String[] args) {
		new SwitchApplication(QuantumVoxel.INSTANCE);
	}
}
