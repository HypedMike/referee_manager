<script lang="ts">
	import { onMount } from 'svelte';

	const { oldScore, newScore } = $props<{
		oldScore: number;
		newScore: number;
	}>();

	const randomId = 'div_id_' + Math.floor(Math.random() * 1000000).toString();
	let scrollFactor = $state(0);

	onMount(() => {
		const maxScroll = 180; // Maximum scroll distance
        scrollFactor = 0; // Reset scroll factor
		setInterval(() => {
			if (scrollFactor < maxScroll) {
				scrollFactor += 20;
			} else {
				scrollFactor = maxScroll;
			}
		}, 100);
    });
</script>

<div class="h-[120px] overflow-hidden text-[100px] font-semibold">
	<div
		id={randomId}
		class="position-relative flex flex-col justify-around"
		style={`top: -${scrollFactor}px; position: relative; transition: top 0.1s ease-in-out;`}
	>
		<span>{oldScore}</span>
		<span>{newScore}</span>
	</div>
</div>
