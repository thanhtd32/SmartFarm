package smart.ai.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import smart.ai.view.pump.MixFormulaFragment;
import smart.ai.view.pump.SetupFormulaFragment;
import smart.ai.view.pump.ViewParameterFragment;

public class PumpAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public PumpAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ViewParameterFragment();
            case 1:
                return new MixFormulaFragment();
            case 2:
                return new SetupFormulaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
