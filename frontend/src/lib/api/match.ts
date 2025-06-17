import { API_BASE_URL } from "./constants";

export interface MatchAPI {
    getMtaches: (params?: {}) => Promise<any>;
    getMatch: (params: { matchId: string }) => Promise<any>;
    getMatchEvents: (params: { matchId: string }) => Promise<any>;
}

function matchAPI(): MatchAPI {
    return {
        getMtaches: async (params?: {}) => {
            const response = await fetch(API_BASE_URL + '/matches', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.json();
        },
        getMatch: async (params: { matchId: string }) => {
            const response = await fetch(`${API_BASE_URL}/matches/${params.matchId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.json();
        },
        getMatchEvents: async (params: { matchId: string }) => {
            const response = await fetch(`${API_BASE_URL}/matches/${params.matchId}/events`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.json();
        }
    };
}

export default matchAPI;