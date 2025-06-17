import matchAPI, { type MatchAPI } from "./match";
import playerAPI from "./player";

export const helper: {
    match: MatchAPI;
    playerAPI: ReturnType<typeof playerAPI>;
} = {
    match: matchAPI(),
    playerAPI: playerAPI(),
}