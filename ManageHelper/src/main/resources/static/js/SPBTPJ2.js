document.addEventListener('DOMContentLoaded', () => {
	document.querySelectorAll('.draggableRow').forEach(item => {
		addEvents(item, draggableEvents);
	});
});

function addEvents(element, events) {
	Object.entries(events).forEach(([event, handler]) => {
		element.addEventListener(event, handler);
	});
};

const draggableEvents = {
	dragstart: (e) => {
		e.target.classList.add('dragging');
	},
	dragend: (e) => {
		e.target.classList.remove('dragging');
		let order = 1;
		document.querySelectorAll('.draggableRow .input-order').forEach(item => {
			item.value = order;
			order++;
		});

	},
	dragover: (e) => {
		e.preventDefault();
		const targetItem = e.target.parentNode;
		const draggingItem = document.getElementsByClassName('dragging')[0];
		if (targetItem === draggingItem) {
			return;
		}
		const bounding = targetItem.getBoundingClientRect();
		const offset = bounding.y + bounding.height / 2;
		const addItem = (e.clientY - offset > 0) ? targetItem.nextSibling : targetItem;
		document.getElementsByClassName('draggableWrap')[0].
			insertBefore(draggingItem, addItem);
	}
};

