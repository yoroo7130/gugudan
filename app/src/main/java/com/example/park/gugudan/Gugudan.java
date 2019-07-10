package com.example.park.gugudan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class Gugudan extends AppCompatActivity {
    String answer;
    TextView tvGugudan, tvAnswer, tvAnswerCount, tvTime;
    ProgressBar bar;
    Button btn[] = new Button[10];
    Integer[] numBtnIDs = { R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };
    Button btnCancle, btnEnter;
    int dan, num;   // tv1와 tv2에 출력할 값을 담는 변수
    int answer_int, my_answer;  // 원래 답과 내가 입력한 답을 담을 변수
    int winCount = 0;   // 정답 횟수를 담는 변수

    public static final int EXIT = Menu.FIRST;
    public static final int RESTART = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gugudan);

        tvGugudan = (TextView)findViewById(R.id.danText);
        tvAnswer = (TextView)findViewById(R.id.answerText);
        tvTime = (TextView)findViewById(R.id.timeText);
        tvAnswerCount = (TextView)findViewById(R.id.answerCountText);
        btnCancle = (Button)findViewById(R.id.buttonCancel);
        btnEnter = (Button)findViewById(R.id.buttonEnter);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        setView();  // 구구단 화면 설정 함수

        startProgress();
        // 숫자 버튼 클릭 시 화면 출력
        for(int i = 0; i < 10; i++){
            btn[i] = (Button)findViewById(numBtnIDs[i]);
            final int num = i;
            btn[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    tvAnswer.setText(tvAnswer.getText().toString() + num);
                }
            });
        }

        // Cancel 버튼 클릭 시
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText("");
            }
        });

        // Enter 버튼 클릭 시
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_answer = Integer.parseInt(tvAnswer.getText().toString());
                answer_int = dan * num;
                if (answer_int == my_answer)
                    winCount++;
                tvAnswer.setText("");
                setView();
            }
        });

    }   // onCreate()

    // 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, EXIT, Menu.NONE, "Exit");
        menu.add(Menu.NONE, RESTART, Menu.NONE, "Restart");
        return super.onCreateOptionsMenu(menu);
    }

    // 메뉴 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case EXIT:
                answer = tvAnswerCount.getText().toString();
                Intent ri = getIntent();
                ri.putExtra("answer", answer);
                setResult(RESULT_OK, ri);
                finish();
                break;

            case RESTART:
                winCount = 0;
                tvAnswer.setText("");
                setView();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setView() {
        Random r = new Random();
        dan = r.nextInt(8) + 2;
        num = r.nextInt(9) + 1;
        tvGugudan.setText(Integer.toString(dan) + " * " + Integer.toString(num));
        tvAnswerCount.setText(Integer.toString(winCount));
    }

    public void startProgress(){
        bar.setProgress(0); // 프로그래스 바의 초기 상태 0으로 초기화
        // Runnable 인터페이스 상속 후 생성 방법
        // 스레드 생성 Runnable
        // Runnable r = new RunnableImplements();
        // Thread t = new Thread(r);
        // t.start();
        Thread tr1= new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=60; i>0; i--){
                        Thread.sleep(1000);

                        bar.incrementProgressBy(1);
                        if(bar.getProgress()==bar.getMax()){    // 진행도 완료시
                            answer = tvAnswerCount.getText().toString();
                            Intent ri = getIntent();
                            ri.putExtra("answer", answer);
                            setResult(RESULT_OK, ri);
                            finish();
                        }else {
                            tvTime.setText(Integer.toString(i) + "초 남았습니다.");
                        }
                    }
                }
                catch (Exception e){
                    Log.e("progressBar","Exception in processing message",e);
                }
            }
        });
        tr1.start();
    }
// Runnable 인터페이스 상속한
//    class RunnableImplements implements Runnable{
//        public void run(){
//            // 작업내용
//        }
//    }

    // 스레드 : Thread 클래스 상속 또는 Runnable 인터페이스를 구현하는 2가지 방법
    // Thread 클래스를 상속받으면 다른 클래스를 상속할 수 없기 때문에 일반적으로 Runnable 인터페이스를 구현
}
