<script lang="ts">
	import { helper } from '$lib/api/helper';
	import { onMount } from 'svelte';

	let matches: any[] = $state([]);

	onMount(() => {
		helper.match
			.getMtaches()
			.then((res) => {
				matches = res;
			})
			.catch((err) => {
				console.error(err);
			});
	});
</script>

<div class="m-10">
	<section>
		<h1>Select a match to monitor</h1>
		<div>
			{#if matches.length > 0}
				<div class="rounded-lg bg-white p-10 pl-0">
					{#each matches as match}
                        <div class="mb-4 rounded-lg bg-gray-100 p-4 shadow-md relative">
                            <div class="absolute top-0 right-3 p-2">
                                <span class="text-xs text-gray-500">{match.id}</span>
                            </div>
                            <div>
                                {match.team1.name} - {match.team2.name}
                            </div>
                            <div class="text-sm text-gray-600">
                                <a href="/match/{match.id}" class="text-blue-500 hover:underline">
                                    View Match Details
                                </a>
                                <a href="/match/{match.id}/monitor" class="ml-4 text-blue-500 hover:underline">
                                    Referee Match
                                </a>
                            </div>
                        </div>
					{/each}
				</div>
			{:else}
				<p>No matches available to monitor.</p>
			{/if}
		</div>
	</section>
</div>
