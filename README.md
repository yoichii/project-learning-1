# githubとは

## githubの本質
githubの本質を一言でいうなら、「バージョン管理を楽にするソフトウェア」です。

色々なソースコードやファイルを複数人で開発していると、下のような作業が必要となります。（開発を楽に進めるため）

* 誰が何をいつ更新したのか、を誰かが整理する必要がある
* ファイルのアーカイブ（=過去のファイル・バージョンの古いファイル）を誰かが管理する必要がある
* 複数人が、あるファイルをそれぞれ修正したとき、誰かがそれらを１つのファイルにまとめあげる必要がある

これらの作業を楽にする（=バージョン管理を楽にする）のがgithubです。

* ファイルの管理者を決めてその人が交通整理をしよう
* 自動でできることは勝手にやっちゃおう

という考えで解決しています。

## githubの仕組み・使い方
こちらを参照してください。
https://backlog.com/ja/git-tutorial/?gclid=Cj0KCQjw7sDlBRC9ARIsAD-pDFrCzUt0nhg9p93EfELwDQBkjRUpKXE0k4hZIYR4rSVCtW5a4pxKo2IaAumPEALw_wcB

# ソースコードの説明
## Controller.java
Controller クラス用のもの。このファイルを実行することで画面が起動する。現在は.setVisible(true)になっている画面を出力するだけ。

## images
使用する画像フォルダ

## othelloScreen
オセロスクリーンパッケージ。import othelloScreen.\*;をすることで、このパッケージ内のクラスを使用することができる。現に、Controller.javaではインポートしている。
