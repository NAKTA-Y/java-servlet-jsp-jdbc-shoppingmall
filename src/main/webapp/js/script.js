const amount = document.getElementById("amount");
// Thumbnails and Hero
const thumbnails = document.querySelectorAll(".thumbnails div");
const hero = document.getElementById("hero");

// Functions
document.addEventListener("click", function (e) {
	// Dynamic Hero
	let found;
	let thumbnailsArray;

	if (e.target.id.includes("thumb")) {
		const index = e.target.id.substring(e.target.id.length - 1);

		thumbnails.forEach((e) => {
			e.children[0].classList.remove("active");
		});

		thumbnailsArray = Array.from(thumbnails);
		found = thumbnailsArray.find((e) => e.id === index);

		found.classList.add("ring-active");
		found.children[0].classList.add("active");

		hero.src = found.children[0].src;

		hero.classList.add("animate-change");
		setTimeout(() => {
			hero.classList.remove("animate-change");
		}, 400);

		return;
	}

	// Total increment/decrement
	if (e.target.id === "plus" || e.target.id === "minus") {
		let amountAdd = parseInt(amount.innerHTML)
		if (e.target.id === "plus") {
			amountAdd += 1;
		} else if (e.target.id === "minus") {
			if (amountAdd <= 0) return;
			amountAdd -= 1;
		}
		amount.innerHTML = amountAdd.toString();
	}
});
