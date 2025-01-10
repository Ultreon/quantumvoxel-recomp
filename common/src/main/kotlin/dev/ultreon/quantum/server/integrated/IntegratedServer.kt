package dev.ultreon.quantum.server.integrated

import dev.ultreon.quantum.server.QuantumVoxelServer

class IntegratedServer : QuantumVoxelServer() {
  override val isDedicatedServer = false
  override val networker: MemoryNetworker = MemoryNetworker()
}