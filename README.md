# BaWay

Baway-4 专4
1.butterknife
    配置：
    1.project build.gradle文件里添加
        buildscript {
          repositories {
            mavenCentral()
             }
          dependencies {
            classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'
            }
        }
    2.Module的build.gradle文件中:

      apply plugin: 'com.android.library'
      apply plugin: 'com.jakewharton.butterknife'
      dependencies {
          compile 'com.jakewharton:butterknife:8.4.0'
          annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
      }
    3.Activity使用，注意view不能为private 或者static

              @BindView(R.id.text_view)
              TextView mTextView;
              @BindView(R.id.button)
              Button mButton;
              @BindView(R.id.edit_text)
              EditText mEditText;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_main);
                  ButterKnife.bind(this); // 绑定
            }
    4.fragment使用
        public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.frag_main, container, false);
                ButterKnife.bind(this, rootView);//这里有些不同
                return rootView;
              }
        }
    5.点击事件使用
        @OnClick(R.id.button)
        public void onButtonClick(View view) {
            Toast.makeText(this, "button被点击了", Toast.LENGTH_SHORT).show();
        }
