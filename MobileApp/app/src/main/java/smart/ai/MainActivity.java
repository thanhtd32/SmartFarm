package smart.ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import smart.ai.adapter.PumpAdapter;
import smart.ai.view.pump.MixFormulaFragment;
import smart.ai.view.pump.SetupFormulaFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {

    @BindView(R.id.tabLayoutPump)
    TabLayout tabLayoutPump;

    @BindView(R.id.viewPagerPump)
    ViewPager viewPagerPump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
    }

    private void addControls() {
        ButterKnife.bind(this);
        setupTabLayout();
    }

    private void setupTabLayout() {
        tabLayoutPump.removeAllTabs();
        tabLayoutPump.addTab(tabLayoutPump.newTab().setText(R.string.title_pump_view_parameter).setIcon(getDrawable(R.drawable.ic_baseline_view_headline_24)));
        tabLayoutPump.addTab(tabLayoutPump.newTab().setText(R.string.title_pump_mix_formula).setIcon(getDrawable(R.drawable.ic_baseline_not_started_24)));
        tabLayoutPump.addTab(tabLayoutPump.newTab().setText(R.string.title_pump_setup_formula).setIcon(getDrawable(R.drawable.ic_baseline_settings_24)));
        tabLayoutPump.setTabGravity(TabLayout.GRAVITY_FILL);

        PumpAdapter pumpAdapter = new PumpAdapter(getSupportFragmentManager(), tabLayoutPump.getTabCount());
        viewPagerPump.setAdapter(pumpAdapter);
        viewPagerPump.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutPump));
        tabLayoutPump.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerPump.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPagerPump.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1:
                        Fragment mixFormulaFragment = pumpAdapter.getRegisteredFragment(position);
                        ((MixFormulaFragment) mixFormulaFragment).settingDatabase();
                        break;
                    case 2:
                        Fragment setupFormulaFragment = pumpAdapter.getRegisteredFragment(position);
                        ((SetupFormulaFragment) setupFormulaFragment).settingDatabase();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}