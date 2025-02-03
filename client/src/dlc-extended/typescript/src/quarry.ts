import { Block, Dimension, Player } from "@ultreon/quantum-voxel/client";

export function tick(block: Block, position: [ number, number, number ], world: Dimension, player: Player): void {
    console.log(block, position, world, player);
}
