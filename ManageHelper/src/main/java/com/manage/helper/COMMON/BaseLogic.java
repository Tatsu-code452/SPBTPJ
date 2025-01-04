package com.manage.helper.COMMON;

public abstract class BaseLogic<T> {

	protected boolean loadData(T dto) {
		return true;
	}

	protected boolean createData(T dto) {
		return true;
	}

	protected boolean saveData(T dto) {
		return true;
	}

	protected boolean createResponse(T dto) {
		return true;
	}

	public boolean execute(T dto) {
		boolean result = true;

		result = loadData(dto);

		if (result) {
			result = createData(dto);
		}
		if (result) {
			result = saveData(dto);
		}
		if (result) {
			result = createResponse(dto);
		}
		return result;
	}
}
