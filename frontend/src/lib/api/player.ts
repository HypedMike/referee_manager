export interface PlayerAPI {
    getPlayers: (params: {
        teamId?: string;
        playerId?: string;
    }) => Promise<any>;
    getPlayer: (params: { playerId: string }) => Promise<any>;
}

function playerAPI(): PlayerAPI {
    return {
        getPlayers: async (params: { teamId?: string; playerId?: string }) => {
            const query = new URLSearchParams(params).toString();
            const response = await fetch(`/api/player?${query}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.json();
        },
        getPlayer: async (params: { playerId: string }) => {
            const response = await fetch(`/api/player/${params.playerId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.json();
        },
    };
}
export default playerAPI;