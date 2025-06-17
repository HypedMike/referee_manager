<script lang="ts">
	import { page } from '$app/state';
	import { helper } from '$lib/api/helper';
	import { onMount } from 'svelte';
	import Scores from './scores.svelte';

	let match: any = $state(null);
	let matchEvents: any[] = $state([]);
	let goals: number[] = $derived<number[]>([
		matchEvents.filter((event) => event.type == 'GOAL' && event.player.team.id == match.team1.id)
			.length,
		matchEvents.filter((event) => event.type == 'GOAL' && event.player.team.id == match.team2.id)
			.length
	]);
    let lastUpdate: number = $state(Date.now());

    function updateMatchEvents(matchId: string | null) {
        if (matchId) {
            helper.match
                .getMatchEvents({
                    matchId: matchId,
                })
                .then((res) => {
                    matchEvents = res;
                    lastUpdate = Date.now();
                })
                .catch((err) => {
                    console.error(err);
                });
        }
    }

	onMount(() => {
		const matchId = $state(page.params.matchId);
		if (matchId) {
			helper.match
				.getMatch({
					matchId: matchId
				})
				.then((res) => {
					match = res;
				})
				.catch((err) => {
					console.error(err);
				});

            updateMatchEvents(matchId);

			setInterval(() => {
                updateMatchEvents(matchId);
			}, 5000);
		}
	});
</script>

<div class="m-10 flex flex-col items-center justify-center h-[90vh]">
    {#if !match}
        <p class="text-gray-500">Loading match details...</p>
    {/if}

    {#if match && !match.team1}
        <p class="text-red-500">Match not found or teams not available.</p>
    {/if}
	{#if match}
		<header>
			<h1 class="mb-4 text-center text-2xl font-bold">
				{match.team1.name} <span class="text-gray-500"> vs </span>
				{match.team2.name}
			</h1>
		</header>
		<section>
            <div class="mb-4 flex items-center justify-around">
                <div class="font-semibold border-b-2 border-gray-300">
                    {lastUpdate ? "Last update: " + new Date(lastUpdate).toLocaleTimeString() : 'Loading...'}
                </div>
            </div>
			<div class="mb-4 flex items-center justify-around">
				<Scores oldScore={goals[0] - 1} newScore={goals[0]} />
                <Scores oldScore={goals[1] - 1} newScore={goals[1]} />
			</div>
			<div class="grid grid-cols-2 gap-4">
				<div>
                    <h2 class="text-lg font-semibold mb-2">Team 1 Events</h2>
                    <div class="border p-4 rounded-lg bg-gray-100">
                        {#each matchEvents.filter(event => event.player.team.id === match.team1.id).slice(0, 5) as event}
                            <div class="mb-2 p-2 bg-white rounded shadow"
                            >{event.type} - {event.player.name} at {new Date(event.timestamp).toLocaleTimeString()}</div>
                        {/each}
                    </div>
                </div>
                <div>
                    <h2 class="text-lg font-semibold mb-2">Team 2 Events</h2>
                    <div class="border p-4 rounded-lg bg-gray-100">
                        {#each matchEvents.filter(event => event.player.team.id === match.team2.id).slice(0, 5) as event}
                            <div class="mb-2 p-2 bg-white rounded shadow"
                            >{event.type} - {event.player.name} at {new Date(event.timestamp).toLocaleTimeString()}</div>
                        {/each}
                    </div>
			    </div>
            </div>
		</section>
	{/if}
</div>
