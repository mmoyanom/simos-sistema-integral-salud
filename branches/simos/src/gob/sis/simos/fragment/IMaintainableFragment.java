package gob.sis.simos.fragment;

import gob.sis.simos.entity.ICuantificable;

public interface IMaintainableFragment {

	public void checkAllItems();
	public void clear();
	public void deleteCheckedItems();
	public ICuantificable findItem(ICuantificable c);
	public void updateItem(ICuantificable c);
}
